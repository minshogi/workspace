clc;
clear;
M=100;   % 총 학습 데이터의 수 한클래스당 5장씩 20개 사용

load normdata



dbx=[];
for i=1:M
    temp=double(S(:,i));
    dbx=[dbx temp];        % 학습행렬이었던 S 대신에 dbx변수로 저장
                           % 10304 * 100 
end

%---------------------------공분산 행렬-----------------------------


A=dbx;                    
L=A'*A;                   % 10304*10304 대신100 * 100 로 계산
[vv dd]=eig(L);           %고유벡터랑 고유값게산
v=[];
d=[];
for i=1:100
    if(dd(i,i)>0)  %주성분벡터 갯수 수정 가능
        v=[v vv(:,i)];   % 100*30
        d=[d dd(i,i)];   % 1*30
    end
end

%-------------------------------올림차순 정렬-----------------------

[B index]=sort(d);           % 고유값 올림차순으로 정렬
ind=zeros(size(index));
dtemp=zeros(size(index));
vtemp=zeros(size(v));
len=length(index);
for i=1:len                 % 고유값과 고유벡터 큰순서대로 다시 정렬
    dtemp(i)=B(len+1-i);
    ind(i)=len+1-index(i);
    vtemp(:,ind(i))=v(:,i);
end
d=dtemp;
v=vtemp;
%-------------------------------고유벡터의 정규화---------------------

for i=1:size(v,2)
    kk=v(:,i);
    temp=sqrt(sum(kk.^2));
    v(:,i)=v(:,i)./temp;
end

%-------------------------------행렬 C의 고유벡터들----------------------

u=[];
for i=1:size(v,2)
    temp=sqrt(d(i));
    u=[u (dbx*v(:,i))];     % C의 고유벡터는 AV 입니다. (10304*100)*(100*30)
end                         %                         = (10304*30) 의 eigenface

%-------------------------------고유 벡터 정규화------------------------
 
for i=1:size(u,2)
    kk=u(:,i);
    temp=sqrt(sum(kk.^2));
    u(:,i)=u(:,i)./temp;
end

%-------------------------------고유 얼굴-------------------------------
figure(4);
for i=1:size(u,2)
    img=reshape(u(:,i),icol,irow);
    img=img';
    img=histeq(img,255);
    subplot(ceil(5),ceil(20),i)
    imshow(img)
    drawnow;
    if i==11
        title('고유얼굴','fontsize',12)
    end
end

save eigendata dbx u icol irow