pipeline {
    agent any
    tools { 
        maven 'maven:3.3.3' 
       
    }
    stages {
        stage('build') {
            steps {
                mvn package
            }
        }
        stage('test') {
            steps {
                mvn test
            }
        }
    }
    post { 
        always { 
            echo 'I will always say Hello again!'
        }
    }
}
