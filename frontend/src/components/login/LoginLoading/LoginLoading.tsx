import gongseekImg from '@/assets/gongseek.png';
import * as S from '@/components/login/LoginLoading/LoginLoading.styles';

const LoginLoading = () => (
	<S.Container>
		<S.LogoImg src={gongseekImg} alt="로딩 중 보여지는 로고 이미지 입니다" />
		<S.Message>로그인 중입니다 잠시만 기다려 주세요</S.Message>
	</S.Container>
);

export default LoginLoading;
