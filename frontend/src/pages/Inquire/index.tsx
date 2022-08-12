import PageLayout from '@/components/layout/PageLayout/PageLayout';
import { mobileTitleSecondary } from '@/constants/titleType';
import * as S from '@/pages/Inquire/index.styles';

const InquirePage = () => {
	const onClickInquireButton = () => {
		window.location.href = 'https://github.com/woowacourse-teams/2022-gong-seek/issues';
	};

	return (
		<S.Container>
			<PageLayout
				width="80%"
				maxWidth="25rem"
				height="18rem"
				flexDirection="column"
				justifyContent="space-around"
				padding="1rem"
			>
				<h2 css={mobileTitleSecondary}>문의하기</h2>
				<p>문의는 깃허브 이슈를 통해서 남겨주세요.</p>
				<S.InquireButton onClick={onClickInquireButton}>문의하기</S.InquireButton>
			</PageLayout>
		</S.Container>
	);
};

export default InquirePage;
