clc;
clear;
M=100;   % 총 학습 데이터의 수 한클래스당 5장씩 20개 사용

%--- 학습데이터 불러와서 데이터로 변환 ---
S=[]'
figure(1);
for i=1:M
    str=sprintf('%d.pgm',i);      % 1.pgm~ 100.pgm 까지
    eval('img=imread(str);');     % 이미지 숫자데이터로 변환
    subplot(ceil(5),ceil(20),i)   % 5 * 20으로 보여주기
    imshow(img)                   % 숫자데이터 사진으로 보여주기
    if i==11                      % 그림창 가운데에 글씨 넣기
        title('학습시킬 100개의 얼굴 이미지', 'fontsize', 12, 'color', 'b')
    end
    drawnow;
    [irow icol]=size(img);       % 이미지의 가로*세로 값 (112 * 92)
    temp=reshape(img', irow*icol,1);   % N^2 * 1의 형태로 변환 
    S=[S temp];                  % S에 학습데이터 모두 저장
                                 % S = 112* 92 한개 100개 
                                 %     10304 * 100
end

save trainingdata S irow icol
