# Meet the Spring team

email: 		contact@garnier.wf
email:      josh@joshlong.com

## list of amazing colleagues (not exhaustive)
toshiaki maki ou maki-san (pour etre poli)

## lifecycle

* -1.1 RuntimeHintsRegistrar
* -1 BeanFactoryInitializationAotProcessor#...
* 0. ingest (xml, java config, component scanning, BeanRegistrar, etc.)
* 1. BeanDefinitions 
* 1.1 BeanFactoryPostProcessor
* 2. beans
* 2.1 BeanPostProcessor#before
* 2.2 constructors, InitializingBean#afterPropertieSet, @PostConstruct
* 2.3 BeanPostProcessor#after
* 2.99 ApplicationRunner/CommandLineRunner
* 3.0 live!