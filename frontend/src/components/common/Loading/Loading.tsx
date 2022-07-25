import * as S from '@/components/common/Loading/Loading.style';

const Loading = () => (
	<S.Container aria-label="현재 로딩 중 화면입니다">
		<h2 hidden>로딩 중 화면입니다</h2>
		<S.FirstSpinner />
		<S.SecondSpinner />
	</S.Container>
);

export default Loading;
