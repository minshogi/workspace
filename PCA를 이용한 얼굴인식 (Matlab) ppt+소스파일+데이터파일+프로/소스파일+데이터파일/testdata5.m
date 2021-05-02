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
        WeightOfImage = t*dbx(:,h);    % 내적으로 학습이미지 가중치계산 
        WW= [WW; WeightOfImage];
    end
    omega=[omega WW];
end

%-------------새 이미지 얻기--------------------------------------
InputImage = input('테스트 데이터 :  \n','s');
InputImage = imread(strcat('D:\Program Files\MATLAB\R2011b\work\PCA3\testData\',InputImage));
figure(5)
subplot(1,2,1)
imshow(InputImage);
colormap('gray');
title('입력얼굴','fontsize',18)
InImage=reshape(double(InputImage)',irow*icol,1);
temp=InImage;
me=mean(temp);
st=std(temp);
temp=(temp-me)*ustd/st+um;
NormImage= temp;
Difference = temp-m;

%-------------재구성 얼굴 표현하기 --------------------------------
%  재구성얼굴 = 평균얼굴 + (eigenface * 새로운이미지의 가중치)
p = [];
aa=size(u,2);
for i=1:aa
    pare = u(:,i)'*NormImage;   %(30*10304) * (10304*1) 가중치 구하기
    p = [p;pare];
end
ReshapedImage = m+u(:,1:aa)*p;   %  m + (10304*30 * 가중치 30*1)
ReshapedImage = reshape(ReshapedImage,icol,irow);
ReshapedImage = ReshapedImage';

%-------------재구성된 이미지 --------------------------------------
subplot(1,2,2)
imagesc(ReshapedImage);
colormap('gray');
title('재구성 영상','fontsize', 12)

InImWeight = [];
for i = 1:size(u,2)
    t= u(:,i)';
    WeightOfInputImage = t*Difference;     %새로들어온 데이터 가중치 계산
    InImWeight = [InImWeight; WeightOfInputImage];
end
ll = 1:size(u,2);
figure(6)
axis on;
stem(ll,InImWeight)
title('입력 얼굴의 가중치','fontsize',12)

%------------  유클리디안 거리 구하기 -------------------------------
e=[];
for i=1:size(omega,2)
    q=omega(:,i);
    DiffWeight= InImWeight-q;
    mag=norm(DiffWeight); 
    e= [e mag];
end

kk = 1:size(e,2);
figure(7)
stem (kk,e)
title('입력 영상에 대한 유클리디안 거리', 'fontsize',12)

[MinimumValue classindex] = min(e)

