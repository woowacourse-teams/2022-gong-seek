import reactDom from 'react-dom';
import { useNavigate } from 'react-router-dom';

import gongseek from '@/assets/gongseek.png';
import { URL } from '@/constants/url';
import * as S from '@/pages/ServerErrorHandling/index.styles';

const ServerErrorHandling = ({ closeErrorPortal }: { closeErrorPortal: () => void }) => {
	const portal = document.getElementById('error-portal');
	const nav = useNavigate();
	if (portal === null) {
		throw new Error('에러포털을 찾지못하였습니다');
	}
	const onHomeButtonClick = () => {
		closeErrorPortal();
		nav(URL.HOME);
	};

	return reactDom.createPortal(
		<S.Container>
			<S.LogoImg src={gongseek} alt="로고 이미지" />
			<S.Message>일시적 서버 장애가 발생하였습니다 </S.Message>
			<S.Message>잠시 후 다시 시도해주세요</S.Message>
			<button onClick={onHomeButtonClick}>홈으로</button>
		</S.Container>,
		portal,
	);
};

export default ServerErrorHandling;
