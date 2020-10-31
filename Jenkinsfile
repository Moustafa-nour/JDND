pipeline {
    agent any
     tools { 
        maven 'maven:3.3.3' 
    }
    stages {
        stage('build') {
            steps {
               sh 'mvn -f JDND/projects/'P04-eCommerce Application'/starter_code/pom.xml package'
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
