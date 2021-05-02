clc;
clear;
M=100;   % �� �н� �������� �� ��Ŭ������ 5�徿 20�� ���

um=100;  % ����ȭ�� ���� ������ ��հ�
ustd=80;  % ����ȭ�� ���� ������ ǥ��������

load trainingdata

%--- �н������� �̹��� ����ȭ ---
for i=1:M
    temp=double(S(:,i));
    m=mean(temp);
    st=std(temp);
    S(:,i)=(temp-m)*ustd/st+um;
end

%--- ����ȭ ó���� �̹��� ---
figure(2)
for i=1:M
    str=strcat(int2str(i),'.jpg');
    img=reshape(S(:,i),icol,irow);  % �̹��� ����� ���ؼ� �ٽ� icol*irow ���·� ��ȯ
    img=img';                       % 92 * 112 -> 112*92�� ��ȯ
    eval('imwrite(img,str)');       % �̹��� �������� �Ҹ���
    subplot(ceil(5),ceil(20),i)
    imshow(img)                     % �̹��� ���
    drawnow;
    if i==11
        title('����ȭ�� �н� �� �̹���','fontsize',12, 'color','r')
    end
end

save normdata S icol irow m
