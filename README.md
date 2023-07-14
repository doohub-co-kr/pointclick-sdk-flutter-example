# pointclick_flutter_example
- 이 프로젝트는 Pointclick SDK Flutter 연동 예제 프로젝트입니다.
- Flutter SDK 예제는 Android 4.1(Jelly Bean, API Level 16) 이상 기기와 JDK 11 이상에서 동작합니다.
- 최신 버전의 Pointclick SDK 사용을 권장합니다.
- 최신 버전의 Android Studio와 Visual Studio Code 사용을 권장합니다. Eclipse에 대한 기술 지원은 하지 않습니다.

Installation
----
1. local.properties 파일에 flutter 설치 경로 입력
    - local.properties 파일에 시스템에 설치된 flutter sdk의 경로를 입력합니다.
    ```
        ... 
        //예시
        flutter.sdk=/Users/유저명/flutter
        ...
    ```
2. 프로젝트 레벨의 build.gradle 파일에 accessKey와 secretkey 입력
    - Flutter를 위한 SDK 연동방법 문서에 기록된 accessKey와 secretKey를 입력합니다.
    ```
        ...
        allprojects {
            repositories {
                google()
                mavenCentral()
                ...
                // PointClick SDK Repository
                maven {
                    url "s3://repo.pointclick.co.kr/releases"
                    credentials(AwsCredentials) {
                        accessKey "제공된 문서에 기록된 accessKey를 사용"
                        secretKey "제공된 문서에 기록된 secretKey를 사용"
                    }
                }
                // Maven Remote Repo에서 PointClick aar파일을 가져올 때 세팅 끝
                ...
            }
        }        
        ...
    ```

Run
----
1. Flutter 프로젝트로 전환
    - Flutter 프로젝트로 전환하기 위해 다음 2가지 flutter 명령어를 순차적으로 실행합니다.
    ```
        flutter clean
        flutter pub get
    ```

2. Settings(윈도우는 Preferences) > Build, Executeion, Deployment > Build Tools > Gradle > Gradle JDK를 11 선택 > Apply 및 OK
