pipeline {
    agent any
     tools { 
        maven 'maven:3.3.3' 
    }
    stages {
        stage('build') {
            steps {
               sh 'mvn --file /var/jenkins_home/workspace/'Pipeline Demo'/projects/'P04-eCommerce Application'/'starter_code'/pom.xml clean package'
               // sh 'mvn clean'
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
