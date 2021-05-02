clc;
clear;

load normdata

%--- 평균얼굴 계산 ---

m=mean(S,2);           % 10304 * 100 인 S데이터의 가로 평균을 모두 구함 -> 10304 * 1
tmimg=uint8(m);        % 각 평균을 8bit-integer로 변환 0~255사이의 값 반환  
img=reshape(tmimg,icol,irow);  % 10304 * 1 -> 92*112 로 다시변환
img=img';              % 112*92로 변환
figure(3);
imshow(img);           %평균얼굴 출력
title('평균 얼굴', 'fontsize',12)

save meanface m



