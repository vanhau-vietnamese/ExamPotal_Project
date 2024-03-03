import React from "react";
import styled from 'styled-components';


/**
 * Header
 */
const StyleDashBoard = styled.div`
  border-radius: 4px;
  display: flex;
`;

const Title = styled.h1`
font-size: 55px;
margin: 10px;
margin-left: 30px;
font-weight: bold;
background: linear-gradient(86.88deg, #7D6AFF 1.38%, #FFB86C 64.35%, #FC2872 119.91%);
color: transparent;
--webkit-background-clip: text;
background-clip: text;
`;

const ActionWrapper = styled.div`
  display: flex;
  position: fixed;
  top: 0;
  right: 0;
  padding: 40px;
`;

const ActionButton = styled.button`
    background: linear-gradient(86.88deg, #7D6AFF 1.38%, #FFB86C 64.35%, #FC2872 119.91%);
    color: transparent;
    --webkit-background-clip: text;
    background-clip: text;
  font-size: 20px;
  font-weight: bold;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 20px;
`;

/**
 * Body
 */
const StyleImage = styled.div`
    display: flex;
    justify-content: center;
    max-height: 100%;
`;

const BodyLesson = styled.div`
    text-align: center;
    font-weight: bold;
    font-size: 30px;
`;

const StyleLesson = styled.div`
  height: 250px;
  width: 250px;
  background-color: #f2f2f2;
  padding: 10px;
  border-radius: 5px;
  margin-bottom: 10px;
  border-radius: 5%;
  margin: 20px;
  .TittleLesson{
    align-items: center;
    font-weight: bold;
    font-size: 20px;
  }
  .ContentLesson{
    margin-top: 30px;
    font-style: italic;
    font-size: 16px;
    margin-bottom: 10px;
  }
  .ButtonLesson button {
  background-color: white;
  color: black;
  transition: background-color 0.3s;
  width: 200px;
  height: 50px;
}

.ButtonLesson button:hover {
  background-color: blue;
  color: white;
}
`;



/**
 * Footer
 */

const FooterWrapper = styled.footer`
  background-color: #f0f0f0;
  padding: 20px;
`;

const FooterContent = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  max-width: 1200px;
  margin: 0 auto;
`;

const FooterColumn = styled.div`
  flex: 1;
  margin-right: 20px;
`;

const FooterHeading = styled.h4`
  color: #333333;
`;

const FooterText = styled.p`
  color: #666666;
`;

const FooterSource = styled.h3`
  text-align: center;
  color: transparent;
  color: transparent;
    text-shadow: 0 0 0.5px rgba(0, 0, 0, 0.5);
`;

const DashBoard = () => {
    return (
        <div>
            <StyleDashBoard>
            <Title>Thranuh</Title>

            <ActionWrapper>
                <ActionButton>Đăng ký</ActionButton>
                <ActionButton>Đăng nhập</ActionButton>
            </ActionWrapper>
          </StyleDashBoard>

          <StyleImage>
            <img src="https://study4.com/media/home/HomeBanner/2/files/Webp.net-resizeimage_69.jpg" alt="Hinh anh"></img>
          </StyleImage>

            <div>
                <BodyLesson>BÀI HỌC MỚI NHẤT</BodyLesson>

                <StyleLesson>
                    <div className="TittleLesson">50 câu hỏi ôn tập Java cơ bản</div>
                    <div className="ContentLesson">
                        <div>Thời gian: 40p</div>
                        <div>Số người đã làm: 1000</div>
                        <div>Số câu: 50</div>
                    </div>
                    <div className="ButtonLesson">
                        <button>Chi tiết</button>
                    </div>
                </StyleLesson>

            </div>


          

          
          

          <FooterWrapper>
            <FooterContent>
                <FooterColumn>
                <FooterHeading>Thông tin 1</FooterHeading>
                <FooterText>Nội dung thông tin 1</FooterText>
                </FooterColumn>
                <FooterColumn>
                <FooterHeading>Thông tin 2</FooterHeading>
                <FooterText>Nội dung thông tin 2</FooterText>
                </FooterColumn>
                <FooterColumn>
                <FooterHeading>Thông tin 3</FooterHeading>
                <FooterText>Nội dung thông tin 3</FooterText>
                </FooterColumn>
            </FooterContent>
            <FooterSource>@ - Bản quyền thuộc về sử hữu của Như Tranh & Văn Hậu </FooterSource>
            </FooterWrapper>
            
       
        </div>
        
      );
}

export default DashBoard;