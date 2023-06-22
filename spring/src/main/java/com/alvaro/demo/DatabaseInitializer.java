package com.alvaro.demo;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alvaro.demo.Comment.Comment;
import com.alvaro.demo.Comment.CommentRepository;
import com.alvaro.demo.Entries.Entry;
import com.alvaro.demo.Entries.EntryRepository;
import com.alvaro.demo.Image.Image;
import com.alvaro.demo.User.User;
import com.alvaro.demo.User.UserRepository;
import com.alvaro.demo.Utils.ImageUtility;

import jakarta.annotation.PostConstruct;

@Component
public class DatabaseInitializer {
    
    @Autowired
    private EntryRepository entryRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() throws IOException{
        File fi1 = new File("./src/main/resources/static/images/work_1.png");
        File fi2 = new File("./src/main/resources/static/images/work_2.png");
        File fi3 = new File("./src/main/resources/static/images/work_3.png");
        File fi4 = new File("./src/main/resources/static/images/work_4.png");
        byte[] fileContent1 = Files.readAllBytes(fi1.toPath());
        byte[] fileContent2 = Files.readAllBytes(fi2.toPath());
        byte[] fileContent3 = Files.readAllBytes(fi3.toPath());
        byte[] fileContent4 = Files.readAllBytes(fi4.toPath());
        Image img1 = new Image("work_1.jpg","image/png",ImageUtility.compressImage(fileContent1));
        Image img2 = new Image("work_2.jpg","image/png",ImageUtility.compressImage(fileContent2));
        Image img3 = new Image("work_3.jpg","image/png",ImageUtility.compressImage(fileContent3));
        Image img4 = new Image("work_4.jpg","image/png",ImageUtility.compressImage(fileContent4));

        System.out.println("Imagenes generadas");
        User admin = this.userRepository.findByName("admin");
        User user = this.userRepository.findByName("user");
        User user1 = this.userRepository.findByName("user1");
        User user2 = this.userRepository.findByName("user2");
        if(user==null){
            String[] roles = {"ROLE_USER"};
            userRepository.save(new User("user", "pass", roles));
        }
        if(user1==null){
            String[] roles = {"ROLE_USER"};
            userRepository.save(new User("user1", "pass", roles));
        }
        if(user2==null){
            String[] roles = {"ROLE_USER"};
            userRepository.save(new User("user2", "pass", roles));
        }
        if(admin==null) {
            String[] roles = {"ROLE_USER","ROLE_ADMIN"};
            userRepository.save(new User("admin", "pass",roles));
        }
        System.out.println("Usuarios generadas");
        
        List<Entry> entries = entryRepository.findAll();

        String contenido1 = "Los ataques informáticos se han convertido en una preocupación creciente en nuestra sociedad digital. Los delincuentes cibernéticos utilizan diversas técnicas para comprometer la seguridad de los sistemas y robar información confidencial. A continuación, mencionan algunos de los ataques informáticos más comunes que debemos tener en cuenta:\n" +
                "Phishing: El phishing es un ataque en el que los estafadores se hacen pasar por entidades legítimas, como bancos o empresas, para engañar a las personas y obtener información confidencial, como contraseñas o números de tarjetas de crédito. Suelen utilizar correos electrónicos o sitios web falsos que se asemejan a los reales.\n" + 
                "Malware: El malware es un software malicioso diseado para dañar o infiltrarse en sistemas informáticos sin el conocimiento o consentimiento del usuario. Puede tomar la forma de virus, troyanos, ransomware u otros tipos de software malintencionado. Los usuarios suelen infectarse al descargar archivos adjuntos o al visitar sitios web comprometidos.\n" + 
                "Ataques de fuerza bruta: En estos ataques, los hackers intentan adivinar contraseñas o claves de cifrado probando todas las combinaciones posibles. Utilizan programas automatizados que generan una gran cantidad de intentos en poco tiempo, explotando la debilidad de contraseñas débiles o predecibles.\n" + 
                "Denegación de servicio (DoS): En este tipo de ataque, los hackers inundan un sistema o una red con una cantidad abrumadora de solicitudes de información, lo que provoca una sobrecarga y la interrupción del servicio. El objetivo es dejar el sistema inaccesible para los usuarios legítimos.\n" + 
                "Ingeniería social: Este ataque se basa en la manipulación psicológica de las personas para obtener información confidencial. Los hackers se hacen pasar por personas de confianza o utilizan técnicas de persuasión para engañar a los usuarios y obtener acceso a sus cuentas o sistemas.\n" + 
                "Estos son solo algunos ejemplos de los ataques informáticos más comunes. Es importante estar informado y tomar precauciones, como mantener el software actualizado, utilizar contraseñas seguras, evitar hacer clic en enlaces sospechosos y estar alerta ante posibles intentos de fraude. La seguridad en línea es responsabilidad de todos, y debemos trabajar juntos para proteger nuestra información y nuestra privacidad en el mundo digital.";
        String contenido2 = "Hoy quiero hablar sobre ChatGPT, una innovadora tecnología de inteligencia artificial desarrollada por OpenAI. ChatGPT es un modelo de lenguaje basado en la arquitectura GPT-3.5, que tiene la capacidad de comprender y generar texto de manera casi humana.\n" +
                "Con ChatGPT, es posible mantener conversaciones fluidas y naturales en diversos temas. Puede responder preguntas, proporcionar explicaciones, generar ideas creativas y mucho más. Su capacidad para comprender el contexto y brindar respuestas coherentes ha sido aclamada por su capacidad para simular una conversación genuina con un ser humano.\n" +
                "Sin embargo, es importante tener en cuenta que ChatGPT también tiene sus limitaciones. Aunque es muy poderoso en términos de generación de texto, puede ser propenso a cometer errores o a proporcionar información incorrecta. Además, puede ser sensible a sesgos inherentes en los datos de entrenamiento, lo que significa que puede reflejar ciertos prejuicios o suposiciones indeseables.\n" +
                "OpenAI ha trabajado arduamente para mitigar estos desafíos y fomentar un uso ético de la tecnología. Han implementado medidas como el uso de guías de uso responsable y la implementación de sistemas de calificación para ayudar a filtrar y mejorar las respuestas generadas.\n" +
                "En resumen, ChatGPT es una herramienta fascinante que muestra el poder de la inteligencia artificial en el procesamiento del lenguaje natural. Con sus capacidades de generación de texto, se ha convertido en una herramienta útil para una amplia gama de aplicaciones, desde asistencia en la resolución de problemas hasta la generación de contenido. Sin embargo, es fundamental utilizarlo con cautela y comprender sus limitaciones, para aprovechar al máximo su potencial y evitar malentendidos o sesgos indeseables.";
        String contenido3 = "La guerra entre Ucrania y Rusia ha sido testigo de una intensificación en el ámbito de los ataques informáticos. Ambos bandos han utilizado técticas cibernéticas para apoyar sus objetivos militares y políticos en el conflicto. Estos ataques informáticos han demostrado ser una herramienta poderosa para socavar la infraestructura, la comunicación y la seguridad de las partes involucradas.\n" +
                "Durante el conflicto, se han registrado varios tipos de ataques cibernéticos. Entre ellos se encuentran:\n" +
                "Ataques de denegación de servicio (DDoS): Ambas partes han empleado ataques DDoS para sobrecargar y colapsar los sistemas de comunicación y los sitios web de los oponentes. Estos ataques tienen como objetivo interrumpir la comunicación y la coordinación entre las fuerzas enemigas.\n" +
                "Operaciones de desinformación: Tanto Ucrania como Rusia han llevado a cabo campañas de desinformación en línea para influir en la opinión pública y distorsionar la percepción de los acontecimientos en el conflicto. Esto incluye la creación y difusión de noticias falsas, la manipulación de redes sociales y la propagación de narrativas sesgadas.\n" +
                "Ciberespionaje: Se han registrado casos de ciberespionaje en el conflicto, en los que los actores cibernéticos han intentado infiltrarse en redes y sistemas para obtener información sensible. Esto puede incluir secretos militares, planes estratégicos y datos de inteligencia.\n" + 
                "Ataques a infraestructuras críticas: Ambas partes han dirigido ataques cibernéticos contra infraestructuras críticas, como redes eléctricas, sistemas de agua y sistemas de transporte. Estos ataques buscan interrumpir los servicios vitales y generar caos en la sociedad.\n" +
                "Es importante destacar que los ataques informáticos en este conflicto no solo tienen implicaciones militares, sino también consecuencias humanitarias y sociales. Los ciberataques pueden afectar a la población civil, interrumpiendo servicios básicos y comprometiendo la seguridad de la información personal.\n" +
                "En conclusión, los ataques informáticos han desempeñado un papel significativo en la guerra entre Ucrania y Rusia, utilizados como una téctica para socavar la infraestructura, la comunicación y la seguridad. Estos ataques subrayan la creciente importancia de la ciberseguridad en el escenario de los conflictos modernos y resaltan la necesidad de medidas defensivas sólidas para proteger los sistemas críticos y la información sensible.";
        String contenido4 = "El avance de la tecnología 5G ha abierto un nuevo mundo de posibilidades en cuanto a conectividad y velocidad de internet. En la actualidad, se está explorando la implementación de la red 5G en aviones comerciales para ofrecer una conexión de alta velocidad a los pasajeros durante sus vuelos.\n" +
                "La llegada del 5G en los aviones promete una experiencia de internet más fluida y rápida, permitiendo a los pasajeros disfrutar de una conexión de alta calidad en todo momento. Esto significaría una mejora significativa en términos de velocidad de descarga y carga de datos, lo que permitiría la transmisión de contenido multimedia sin problemas y una navegación más ágil.\n" +
                "Además, la tecnología 5G en los aviones también podría beneficiar a la tripulación y al personal de la aerolínea al mejorar las comunicaciones internas y las operaciones en vuelo. Se podrían realizar tareas como la transmisión en tiempo real de datos de los sistemas de a bordo, el monitoreo remoto de equipos y la optimización de los procesos logísticos.\n" +
                "Sin embargo, hay desafíos técnicos y regulatorios a considerar para implementar el 5G en los aviones de manera segura y eficiente. La interferencia con las comunicaciones de vuelo y la gestión del espectro son algunos de los aspectos que deben abordarse cuidadosamente.\n" +
                "A pesar de los desafíos, la implementación del 5G en los aviones es un paso emocionante hacia el futuro de la conectividad aérea. Proporcionará una experiencia de internet mejorada para los pasajeros y ofrecerá nuevas oportunidades para la eficiencia operativa en la industria de la aviación.";
        
        if(entries.size()==0){
            Entry entry1 = new Entry("Ataques Informáticos",contenido1,user,img1);
            Entry entry2 = new Entry("Chat GPT",contenido2,user1,img2);
            Entry entry3 = new Entry("Hackers en la guerra",contenido3,user,img3);
            Entry entry4 = new Entry("5G en aviones",contenido4,user,img4);
            Comment comment1 = new Comment("Me ha parecido muy util e interesante",entry1,user1);
            Comment comment2 = new Comment("No sabia nada del tema y me he enterado bastante bien :)",entry1,user2);
            Comment comment3 = new Comment("Guau increible",entry2,user2);
            System.out.println("Entradas y comentarios listos");

            entryRepository.save(entry1);
            entryRepository.save(entry2);
            entryRepository.save(entry3);
            entryRepository.save(entry4);
            System.out.println("Post Guardados");
            commentRepository.save(comment1);
            commentRepository.save(comment2);
            commentRepository.save(comment3);
            System.out.println("Comments Guardados");
            
        }
       
        
    }

}
