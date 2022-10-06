import Card from '@/components/@common/Card/Card';
import { mobileTitleSecondary } from '@/constants/titleType';
import * as S from '@/pages/Inquire/index.styles';
import { InquireCardStyle } from '@/styles/cardStyle';

const InquirePage = () => {
	const onClickInquireButton = () => {
		window.location.href = 'https://github.com/woowacourse-teams/2022-gong-seek/issues';
	};

	return (
		<S.Container>
			<Card {...InquireCardStyle}>
				<h2 css={mobileTitleSecondary}>문의하기</h2>
				<p>문의는 깃허브 이슈를 통해서 남겨주세요.</p>
				<S.InquireButton onClick={onClickInquireButton}>문의하기</S.InquireButton>
			</Card>
		</S.Container>
	);
};

export default InquirePage;
