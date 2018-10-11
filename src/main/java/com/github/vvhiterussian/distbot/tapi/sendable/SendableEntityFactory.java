package com.github.vvhiterussian.distbot.tapi.sendable;

import com.github.vvhiterussian.distbot.model.Chat;
import com.github.vvhiterussian.distbot.model.PhotoSize;
import com.github.vvhiterussian.distbot.model.Voice;
import org.springframework.core.io.Resource;

import java.util.HashMap;
import java.util.Map;

//@Component
//@Configuration
public class SendableEntityFactory {

    private static Map<Class, String> entitySendableTypes = new HashMap<>();
    private static Map<Class, String> entitySendableMethods = new HashMap<>();
    static {
        entitySendableTypes.put(Voice.class, "voice");
        entitySendableTypes.put(PhotoSize.class, "photo");

        entitySendableMethods.put(Voice.class, "sendVoice");
        entitySendableMethods.put(PhotoSize.class, "sendPhoto");
    }

//    @Bean
//    @Qualifier("entitySendableTypes")
//    public Map<Class, String> entitySendableTypes() {
//        Map<Class, String> entitySendableTypes = new HashMap<>();
//        entitySendableTypes.put(Voice.class, "voice");
//        return entitySendableTypes;
//    }
//
//    @Bean
//    @Qualifier("entitySendableMethods")
//    public Map<Class, String> entitySendableMethods() {
//        Map<Class, String> entitySendableMethods = new HashMap<>();
//        entitySendableMethods.put(Voice.class, "sendVoice");
//        return entitySendableMethods;
//    }
//
//    private final Map<Class, String> entitySendableTypes;
//    private final Map<Class, String> entitySendableMethods;
//
//
//    public SendableEntityFactory(@Autowired @Qualifier("entitySendableTypes") Map<Class, String> entitySendableTypes,
//                                 @Autowired @Qualifier("entitySendableMethods") Map<Class, String> entitySendableMethods) {
//        this.entitySendableTypes = entitySendableTypes;
//        this.entitySendableMethods = entitySendableMethods;
//    }
//
//    public SendableEntityFactory() {
//        entitySendableTypes =
//    }

    public static SendableEntity createSendableEntity(Chat chat, Resource resource, Class clazz) {
        if (entitySendableTypes.containsKey(clazz) && entitySendableMethods.containsKey(clazz)) {
            SendableEntity sendableEntity = new SendableEntity(entitySendableMethods.get(clazz));
            sendableEntity.getMap().add("chat_id", chat.getId());
            sendableEntity.getMap().add(entitySendableTypes.get(clazz), resource);

            return sendableEntity;
        }
        return null;
    }
}
