pipeline {
    agent { docker { image 'maven' } }
     tools { 
        maven 'maven' 
        // jdk 'JDK:9.0.4'
    }
    stages {
        stage('build') {
            steps {
                sh ' mvn "-f /var/jenkins_home/workspace/'project demo'/projects/'P04-eCommerce Application'/starter_code" clean'
              //  sh 'mvn clean'
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
