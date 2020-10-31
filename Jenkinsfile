pipeline {
    agent { docker { image 'maven:3.3.3' } }
     tools { 
        maven 'maven' 
        // jdk 'JDK:9.0.4'
    }
    stages {
        stage('build') {
            steps {
              // sh 'mvn clean package -f /var/jenkins_home/workspace/'Pipeline Demo'/projects/'P04-eCommerce Application'/'starter_code'/pom.xml '
                sh 'mvn clean'
            }
        }
        stage('test') {
            steps {
               sh 'mvn test'
            }
        }
    }
    post { 
        always { 
            echo 'I will always say Hello again!'
        }
    }
}
