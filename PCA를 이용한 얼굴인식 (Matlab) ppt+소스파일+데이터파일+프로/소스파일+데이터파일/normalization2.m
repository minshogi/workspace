clc;
clear;
M=100;   % 총 학습 데이터의 수 한클래스당 5장씩 20개 사용

um=100;  % 정규화를 위한 임의의 평균값
ustd=80;  % 정규화를 위한 임의의 표준편차값

load trainingdata

%--- 학습데이터 이미지 정규화 ---
for i=1:M
    temp=double(S(:,i));
    m=mean(temp);
    st=std(temp);
    S(:,i)=(temp-m)*ustd/st+um;
end

%--- 정규화 처리후 이미지 ---
figure(2)
for i=1:M
    str=strcat(int2str(i),'.jpg');
    img=reshape(S(:,i),icol,irow);  % 이미지 출력을 위해서 다시 icol*irow 형태로 변환
    img=img';                       % 92 * 112 -> 112*92로 변환
    eval('imwrite(img,str)');       % 이미지 쓰기위해 불르기
    subplot(ceil(5),ceil(20),i)
    imshow(img)                     % 이미지 출력
    drawnow;
    if i==11
        title('정규화된 학습 얼굴 이미지','fontsize',12, 'color','r')
    end
end

save normdata S icol irow m
