clc
clear

M=100;   % �� �н� �������� �� ��Ŭ������ 5�徿 20�� ���
load eigendata;
load normdata m;
um=100;  % ����ȭ�� ���� ������ ��հ�
ustd=80;  % ����ȭ�� ���� ������ ǥ��������


%----------�н��󱼿������տ��� �� ���� ����ġ ���---------------------

omega=[];
for h=1:size(dbx,2);
    WW=[];
    for i=1:size(u,2)
        t=u(:,i)';
        WeightOfImage = t*dbx(:,h);    % �������� �н��̹��� ����ġ��� 
        WW= [WW; WeightOfImage];
    end
    omega=[omega WW];
end

%-------------�� �̹��� ���--------------------------------------
InputImage = input('�׽�Ʈ ������ :  \n','s');
InputImage = imread(strcat('D:\Program Files\MATLAB\R2011b\work\PCA3\testData\',InputImage));
figure(5)
subplot(1,2,1)
imshow(InputImage);
colormap('gray');
title('�Է¾�','fontsize',18)
InImage=reshape(double(InputImage)',irow*icol,1);
temp=InImage;
me=mean(temp);
st=std(temp);
temp=(temp-me)*ustd/st+um;
NormImage= temp;
Difference = temp-m;

%-------------�籸�� �� ǥ���ϱ� --------------------------------
%  �籸���� = ��վ� + (eigenface * ���ο��̹����� ����ġ)
p = [];
aa=size(u,2);
for i=1:aa
    pare = u(:,i)'*NormImage;   %(30*10304) * (10304*1) ����ġ ���ϱ�
    p = [p;pare];
end
ReshapedImage = m+u(:,1:aa)*p;   %  m + (10304*30 * ����ġ 30*1)
ReshapedImage = reshape(ReshapedImage,icol,irow);
ReshapedImage = ReshapedImage';

%-------------�籸���� �̹��� --------------------------------------
subplot(1,2,2)
imagesc(ReshapedImage);
colormap('gray');
title('�籸�� ����','fontsize', 12)

InImWeight = [];
for i = 1:size(u,2)
    t= u(:,i)';
    WeightOfInputImage = t*Difference;     %���ε��� ������ ����ġ ���
    InImWeight = [InImWeight; WeightOfInputImage];
end
ll = 1:size(u,2);
figure(6)
axis on;
stem(ll,InImWeight)
title('�Է� ���� ����ġ','fontsize',12)

%------------  ��Ŭ����� �Ÿ� ���ϱ� -------------------------------
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
title('�Է� ���� ���� ��Ŭ����� �Ÿ�', 'fontsize',12)

[MinimumValue classindex] = min(e)

