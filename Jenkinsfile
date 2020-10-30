pipeline {
    agent { docker { image 'maven 3.6.3' } }
    stages {
        stage('build') {
            steps {
                mvn clean
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
