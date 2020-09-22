# spring-boot-sentry

Demo project that shows how to setup sentry for spring-boot apps with [io.sentry.sentry-spring][sentry-spring-docs].

## Official sentry-spring docs

Take a look at the [official sentry-spring docs][sentry-spring-docs].

## Step by step

- Sentry Setup
  1. Create a new project, if you don' have one yet
  1. Go to the settings of your project and copy your DNS string at the _Client Keys (DNS)_ page
  
- Project Setup
  1. Add the [latest][sentry-spring-installations] version of `sentry-spring` to your dependencies.
  1. Setup the environment variable: `SENTRY_DNS=YOUR_DNS_STRING_HERE`
  1. Create the file `src/main/resources/sentry.properties` and set at least this `timeout = 15000`
  (the default timeout is really short if your network suffers from bad latency)
  1. Create/Update your configuration bean with (check the official docs for more info):
  ```kotlin
  import io.sentry.spring.SentryExceptionResolver
  import io.sentry.spring.SentryServletContextInitializer
  import org.springframework.boot.web.servlet.ServletContextInitializer
  import org.springframework.context.annotation.Bean
  import org.springframework.context.annotation.Configuration
  import org.springframework.web.servlet.HandlerExceptionResolver
  
  @Configuration
  class Configuration {
  
      @Bean
      fun sentryExceptionResolver(): HandlerExceptionResolver = SentryExceptionResolver()
  
      @Bean
      fun sentryServletContextInitializer(): ServletContextInitializer = SentryServletContextInitializer()
  }
  ```

Done!

Run you application and test some crashing exception.
Go to the issues page of your sentry project and check if something got really captured.

## TODO

- More metadata setup (release, user, extras, environment, etc)

## Running a local Sentry with docker

Follow this: https://hub.docker.com/_/sentry

TODO docker-compose.yml and document it how to set it up

[sentry-spring-docs]: https://docs.sentry.io/platforms/java/guides/spring/
[sentry-spring-installations]: https://docs.sentry.io/clients/java/integrations/#installation-6
