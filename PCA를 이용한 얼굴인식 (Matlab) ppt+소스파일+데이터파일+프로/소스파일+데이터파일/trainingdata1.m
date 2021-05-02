clc;
clear;
M=100;   % �� �н� �������� �� ��Ŭ������ 5�徿 20�� ���

%--- �н������� �ҷ��ͼ� �����ͷ� ��ȯ ---
S=[]'
figure(1);
for i=1:M
    str=sprintf('%d.pgm',i);      % 1.pgm~ 100.pgm ����
    eval('img=imread(str);');     % �̹��� ���ڵ����ͷ� ��ȯ
    subplot(ceil(5),ceil(20),i)   % 5 * 20���� �����ֱ�
    imshow(img)                   % ���ڵ����� �������� �����ֱ�
    if i==11                      % �׸�â ����� �۾� �ֱ�
        title('�н���ų 100���� �� �̹���', 'fontsize', 12, 'color', 'b')
    end
    drawnow;
    [irow icol]=size(img);       % �̹����� ����*���� �� (112 * 92)
    temp=reshape(img', irow*icol,1);   % N^2 * 1�� ���·� ��ȯ 
    S=[S temp];                  % S�� �н������� ��� ����
                                 % S = 112* 92 �Ѱ� 100�� 
                                 %     10304 * 100
end

save trainingdata S irow icol
