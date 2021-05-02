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
        WeightOfImage = dot(t,dbx(:,h)');
        WW= [WW; WeightOfImage];
    end
    omega=[omega WW];
end

%-------------101~200������ �׽�Ʈ ������----------------------------
Err=[]'
for i=101:200
    InputImage = sprintf('%d.pgm',i);
    InputImage = imread(strcat('D:\Program Files\MATLAB\R2011b\work\PCA3\testData\',InputImage));
    figure(7)
    subplot(ceil(5),ceil(20),i-100)   % 5 * 20���� �����ֱ�
    imshow(InputImage)                   % ���ڵ����� �������� �����ֱ�
    if i==11                      % �׸�â ����� �۾� �ֱ�
        title('�׽�Ʈ�� 100���� �� �̹���', 'fontsize', 12, 'color', 'b')
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

%-------------�籸���� �̹��� --------------------------------------

    InImWeight = [];
    for i = 1:size(u,2)
      t= u(:,i)';
      WeightOfInputImage = dot(t,Difference');
      InImWeight = [InImWeight; WeightOfInputImage];
    end
%------------  ��Ŭ����� �Ÿ� ���ϱ� -------------------------------
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
%------------ 101~200 �׽�Ʈ������ ������ �м� ---------------------
Err=reshape(Err,5,20);
figure(8);
plot((1:100),Err(1:100),'+')
axis equal;



