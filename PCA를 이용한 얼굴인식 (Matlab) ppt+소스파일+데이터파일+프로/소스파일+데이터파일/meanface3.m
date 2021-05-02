clc;
clear;

load normdata

%--- ��վ� ��� ---

m=mean(S,2);           % 10304 * 100 �� S�������� ���� ����� ��� ���� -> 10304 * 1
tmimg=uint8(m);        % �� ����� 8bit-integer�� ��ȯ 0~255������ �� ��ȯ  
img=reshape(tmimg,icol,irow);  % 10304 * 1 -> 92*112 �� �ٽú�ȯ
img=img';              % 112*92�� ��ȯ
figure(3);
imshow(img);           %��վ� ���
title('��� ��', 'fontsize',12)

save meanface m



