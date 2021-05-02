clc;
clear;
M=100;   % �� �н� �������� �� ��Ŭ������ 5�徿 20�� ���

load normdata



dbx=[];
for i=1:M
    temp=double(S(:,i));
    dbx=[dbx temp];        % �н�����̾��� S ��ſ� dbx������ ����
                           % 10304 * 100 
end

%---------------------------���л� ���-----------------------------


A=dbx;                    
L=A'*A;                   % 10304*10304 ���100 * 100 �� ���
[vv dd]=eig(L);           %�������Ͷ� �������Ի�
v=[];
d=[];
for i=1:100
    if(dd(i,i)>0)  %�ּ��к��� ���� ���� ����
        v=[v vv(:,i)];   % 100*30
        d=[d dd(i,i)];   % 1*30
    end
end

%-------------------------------�ø����� ����-----------------------

[B index]=sort(d);           % ������ �ø��������� ����
ind=zeros(size(index));
dtemp=zeros(size(index));
vtemp=zeros(size(v));
len=length(index);
for i=1:len                 % �������� �������� ū������� �ٽ� ����
    dtemp(i)=B(len+1-i);
    ind(i)=len+1-index(i);
    vtemp(:,ind(i))=v(:,i);
end
d=dtemp;
v=vtemp;
%-------------------------------���������� ����ȭ---------------------

for i=1:size(v,2)
    kk=v(:,i);
    temp=sqrt(sum(kk.^2));
    v(:,i)=v(:,i)./temp;
end

%-------------------------------��� C�� �������͵�----------------------

u=[];
for i=1:size(v,2)
    temp=sqrt(d(i));
    u=[u (dbx*v(:,i))];     % C�� �������ʹ� AV �Դϴ�. (10304*100)*(100*30)
end                         %                         = (10304*30) �� eigenface

%-------------------------------���� ���� ����ȭ------------------------
 
for i=1:size(u,2)
    kk=u(:,i);
    temp=sqrt(sum(kk.^2));
    u(:,i)=u(:,i)./temp;
end

%-------------------------------���� ��-------------------------------
figure(4);
for i=1:size(u,2)
    img=reshape(u(:,i),icol,irow);
    img=img';
    img=histeq(img,255);
    subplot(ceil(5),ceil(20),i)
    imshow(img)
    drawnow;
    if i==11
        title('������','fontsize',12)
    end
end

save eigendata dbx u icol irow