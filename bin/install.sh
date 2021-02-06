# Step1、克隆项目
if ! [ -x "$(command -v git)" ]; then
  echo "Error: Git is not installed, please install it first!"
  exit 0
else
  if ! [ ! -d "ok-jwt-interceptor" ]; then
    echo "项目已存在，正在拉取最新代码..."
    cd ok-jwt-interceptor
    git pull origin master
  else
    echo "开始克隆项目..."
    git clone https://gitee.com/ok-tool/ok-jwt-interceptor.git
    cd ok-jwt-interceptor
  fi
fi

# Step2、安装项目
if ! [ -x "$(command -v mvn)" ]; then
  echo "Error: Maven is not installed, please install it first!"
  exit 0
else
  echo "开始安装项目..."
  mvn install -Dmaven.test.skip=true
fi

echo ":) 一切就绪，请尽情使用！如果有任何想法或意见，欢迎 issue 或 pr，QQ 交流群：956194623"
