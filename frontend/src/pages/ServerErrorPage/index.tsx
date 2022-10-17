import gongseek from '@/assets/gongseek.png';
import { URL } from '@/constants/url';
import * as S from '@/pages/ServerErrorPage/index.styles';

const ServerErrorPage = () => (
	<S.Container>
		<S.LogoImg src={gongseek} alt="로고 이미지" />
		<S.Message>일시적 서버 장애가 발생하였습니다 </S.Message>
		<S.Message>잠시 후 다시 시도해주세요</S.Message>
		<S.HomeButton onClick={() => (window.location.href = `${URL.HOME}`)}>홈으로 가기</S.HomeButton>
	</S.Container>
);

export default ServerErrorPage;
