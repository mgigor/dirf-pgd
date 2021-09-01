package serpro.dirf.pgd;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.spi.PersistenceProviderResolver;
import javax.persistence.spi.PersistenceProviderResolverHolder;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.SneakyThrows;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import serpro.dirf.pgd.constant.EImages;
import serpro.dirf.pgd.util.HibernateUtil;
import serpro.dirf.pgd.util.Utils;
import serpro.dirf.pgd.util.UtilsFile;

@RestController
@RequestMapping("/dirf")
@SuppressWarnings("unchecked")
public class TesteController {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private EntityManagerStub ems;

	@Autowired
	private PersistenceProviderStub pps;

	@SneakyThrows
	@Transactional
	@GetMapping("/importar")
	public void importar() {
		
		
		//https://gasparbarancelli.com/post/multi-tenancy-com-hibernate-e-spring-boot
		configurarHibernateUtil();

		Dirf dirf = new Dirf();

		Method declaredMethod = dirf.getClass().getDeclaredMethod("importar", String.class);
		declaredMethod.setAccessible(true);
		Boolean importacaoOK = (Boolean) declaredMethod.invoke(dirf, "C:/Users/mgigo/Desktop/dirf.DEC");
		System.out.println("Importação: " + importacaoOK);

	}

	@SneakyThrows
	@Transactional
	@GetMapping("/relatorio")
	public void relatorio() {

		configurarHibernateUtil();

		EntityManagerFactoryInfo info = (EntityManagerFactoryInfo) em.getEntityManagerFactory();
		Connection connection = info.getDataSource().getConnection();

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("PES_ID", 10);
		param.put("IMPRIMIRA_CPVT_PJ", true);
		param.put("REPORT_CONNECTION", connection);

		try (InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(UtilsFile.getRelatorioDir() + "Comprovantes.jasper")) {
			param.putAll(montarParametros());

			JasperReport report = (JasperReport) JRLoader.loadObject(inputStream);

			JasperPrint print = JasperFillManager.fillReport((JasperReport) report, param, (Connection) connection);

			byte[] output = JasperExportManager.exportReportToPdf(print);
			JasperExportManager.exportReportToPdfFile(print, "teste.pdf");
		} catch (JRException | IllegalArgumentException | SecurityException | IOException e) {
			e.printStackTrace();
		}

	}

	private Map<String, Object> montarParametros() {
		Map<String, Object> param = new HashMap<>();
		param.put("BRASAO", EImages.SRFDirf2018.getImageName());
		param.put("SUBREPORT_DIR", UtilsFile.getRelatorioDir());
		param.put("VERSAO", "Vers" + Utils.getVersao());
		param.put("RODAPE", "RelatorioDIRFPadrao_Rodape.jasper");
		param.put("RODAPE_OBS", "RelatorioDIRFPadrao_RodapeObs.jasper");
		param.put("DATA", (new SimpleDateFormat("dd/MM/yyyy")).format(new Date()));
		param.put("BRASAO_REPUBLICA", EImages.BrasaoRepublica.getImageName());
		param.put("LOGO_RFB", EImages.ReceitaComprovante.getImageName());
		param.put("URL_RFB", "receita.economia.gov.br");
		param.put("NOME_MINISTERIO", "Ministda Economia");
		param.put("NOME_SECRETARIA_RFB", "Secretaria Especial da Receita Federal do Brasil");
		param.put("IS_IGNORE_PAGINATION", new Boolean(true));
		return param;
	}

	@SneakyThrows
	private void configurarEntityManager() {
		PersistenceProviderResolver persistenceProviderResolver = PersistenceProviderResolverHolder
				.getPersistenceProviderResolver();

		Field providersField = persistenceProviderResolver.getClass().getDeclaredField("providers");
		providersField.setAccessible(true);

		HashMap<Object, Object> providers = (HashMap<Object, Object>) providersField.get(persistenceProviderResolver);

		Class<?> cacheKeyClass = persistenceProviderResolver.getClass().getDeclaredClasses()[2];
		Constructor<?> constructor = cacheKeyClass.getDeclaredConstructors()[0];
		constructor.setAccessible(true);
		Object cacheKey = constructor.newInstance(persistenceProviderResolver,
				Thread.currentThread().getContextClassLoader());

		Class<?> persistenceProviderReferenceClass = persistenceProviderResolver.getClass().getDeclaredClasses()[0];
		Constructor<?> constructor2 = persistenceProviderReferenceClass.getDeclaredConstructors()[0];
		constructor2.setAccessible(true);
		Object persistenceProviderReference = constructor2.newInstance(persistenceProviderResolver, Arrays.asList(pps),
				new ReferenceQueue<>(), cacheKey);

		providers.put(cacheKey, persistenceProviderReference);
	}

	private void configurarHibernateUtil() {
		configurarEntityManager();

		EntityManagerFactoryStub emfs = new EntityManagerFactoryStub(ems);

		setStaticValuePrivateFieldToHibernateUtil("emfMaster", emfs);
		setStaticValuePrivateFieldToHibernateUtil("emfDirf", emfs);

		setStaticValuePrivateFieldToHibernateUtil("entityManagerDirf", ems);
		setStaticValuePrivateFieldToHibernateUtil("entityManagerMaster", ems);
		setStaticValuePrivateFieldToHibernateUtil("entityPai", ems);
		setStaticValuePrivateFieldToHibernateUtil("entityFilhos", ems);

		setStaticValuePrivateFieldToHibernateUtil("OPCAO_MULTITENANT", false);
		setStaticValuePrivateFieldToHibernateUtil("OPCAO_SHOW_SQL", true);

	}

	@SneakyThrows
	private void setStaticValuePrivateFieldToHibernateUtil(String fieldName, Object value) {
		Field field = HibernateUtil.class.getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(null, value);
	}

}
