import gongseek from '@/assets/gongseek.png';
import * as S from '@/pages/NotFound/index.styles';

const NotFound = () => (
	<S.Container>
		<S.LogoImage src={gongseek} alt="로고 이미지" />
		<S.ErrorContent>
			찾을수 없는 페이지입니다.
			<br /> 경로를 다시 확인해보세요 :D
		</S.ErrorContent>
		<S.NavigateLink to="/">홈으로 이동</S.NavigateLink>
	</S.Container>
);

export default NotFound;
