clc
clear

M=100;   % 총 학습 데이터의 수 한클래스당 5장씩 20개 사용
load eigendata;
load normdata m;
um=100;  % 정규화를 위한 임의의 평균값
ustd=80;  % 정규화를 위한 임의의 표준편차값


%----------학습얼굴영상집합에서 각 얼굴의 가중치 계산---------------------

omega=[];
for h=1:size(dbx,2);
    WW=[];
    for i=1:size(u,2)
        t=u(:,i)';
        WeightOfImage = dot(t,dbx(:,h)');
        WW= [WW; WeightOfImage];
    end
    omega=[omega WW];
end

%-------------101~200까지의 테스트 데이터----------------------------
Err=[]'
for i=101:200
    InputImage = sprintf('%d.pgm',i);
    InputImage = imread(strcat('D:\Program Files\MATLAB\R2011b\work\PCA3\testData\',InputImage));
    figure(7)
    subplot(ceil(5),ceil(20),i-100)   % 5 * 20으로 보여주기
    imshow(InputImage)                   % 숫자데이터 사진으로 보여주기
    if i==11                      % 그림창 가운데에 글씨 넣기
        title('테스트할 100개의 얼굴 이미지', 'fontsize', 12, 'color', 'b')
    end
    drawnow;
    
    InImage=reshape(double(InputImage)',irow*icol,1);
    temp=InImage;
    me=mean(temp);
    st=std(temp);
    temp=(temp-me)*ustd/st+um;
    NormImage= temp;
    Difference = temp-m;

    p = [];
    aa=size(u,2);
    for i=1:aa
        pare = dot(NormImage,u(:,i));
        p = [p;pare];
    end
    ReshapedImage = m+ u(:,1:aa)*p;
    ReshapedImage = reshape(ReshapedImage,icol,irow);
    ReshapedImage = ReshapedImage';

%-------------재구성된 이미지 --------------------------------------

    InImWeight = [];
    for i = 1:size(u,2)
      t= u(:,i)';
      WeightOfInputImage = dot(t,Difference');
      InImWeight = [InImWeight; WeightOfInputImage];
    end
%------------  유클리디안 거리 구하기 -------------------------------
    e=[];
    for i=1:size(omega,2)
      q=omega(:,i);
      DiffWeight= InImWeight-q;
      mag=norm(DiffWeight);
      e= [e mag];
    end

    kk = 1:size(e,2);
    [MinimumValue classindex] = min(e);
    Err=[Err classindex];
end
%------------ 101~200 테스트데이터 오류율 분석 ---------------------
Err=reshape(Err,5,20);
figure(8);
plot((1:100),Err(1:100),'+')
axis equal;



