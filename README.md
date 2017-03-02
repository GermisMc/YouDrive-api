###How to build
1. Install MySQL database and change youdrive-api.yml [config](https://github.com/GermisMc/YouDrive-api/blob/master/src/main/resources/youdrive-api.yml) if needed.
1. run `gradlew clean distZip`

###How to run
1. Unpack archive (zip or tar) from `build/distributions`.
1. Navigate to bin folder
1. Run `youdrive-api db migrate ../youdrive-api.yml`. This command applies database migrations.
1. Run`youdrive-api server ../youdrive-api.yml`. This command runs server on port 8080.

###How to use
* `http://host:8080/youdrive/admin` - admin utils like server metrics, health checks and others.
* `http://host:8080/youdrive/api/v1` - youdrive api
