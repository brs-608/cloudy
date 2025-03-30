//package com.burmic.cloudy.Configurations;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CorsConfig {
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins("*")  // 🔥 Sab origins allow kar diye!
//                        .allowedMethods("*")  // 🔥 Sab methods allow kar diye! (GET, POST, PUT, DELETE, etc.)
//                        .allowedHeaders("*")  // 🔥 Sare headers allow kar diye!
//                        .allowCredentials(true);  // 💡 Agar JWT ya cookies use kar raha hai to isko true rakh
//            }
//        };
//    }
//}
//
