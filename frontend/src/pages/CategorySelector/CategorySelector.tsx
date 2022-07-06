import PageLayout from '@/components/layout/PageLayout/PageLayout';
import { mobileTitleSecondary } from '@/constants/titleType';
import styled from '@emotion/styled';
import { CATEGORY_TYPE } from '@/constants/categoryType';
import { Link } from 'react-router-dom';

const Container = styled.div`
	display: flex;
	flex-direction: column;
	width: 100%;
	height: calc(100vh - 15rem);
	justify-content: center;
	align-items: center;
`;

const CategoryButtonBox = styled.div`
	display: flex;
	gap: 1.5rem;
`;

const CategoryButton = styled.button<{ categoryType: 'error' | 'discussion' }>`
	box-shadow: 0px 8px 24px ${({ theme }) => theme.boxShadows.secondary};
	background-color: ${({ categoryType }) => CATEGORY_TYPE[categoryType].color};
	border-color: transparent;
	border-radius: ${({ theme }) => theme.fonts.SIZE_010};
	color: white;
	width: ${({ theme }) => theme.fonts.SIZE_080};
	height: ${({ theme }) => theme.fonts.SIZE_080};
	font-size: ${({ theme }) => theme.fonts.SIZE_016};
	cursor: pointer;
	&:hover,
	&:active {
		background-color: ${({ categoryType }) => CATEGORY_TYPE[categoryType].hoverColor};
	}
`;

const StyledLink = styled(Link)`
	text-decoration: none;
`;

const CategorySelector = () => (
	<Container>
		<PageLayout width="80%" height="16rem" flexDirection="column" justifyContent="space-around">
			<h2 css={mobileTitleSecondary}>글쓰기 카테고리 선택</h2>
			<CategoryButtonBox>
				<StyledLink to="/article/error">
					<CategoryButton type="button" categoryType="error">
						에러
					</CategoryButton>
				</StyledLink>
				<StyledLink to="/article/discussion">
					<CategoryButton type="button" categoryType="discussion">
						토론
					</CategoryButton>
				</StyledLink>
			</CategoryButtonBox>
		</PageLayout>
	</Container>
);

export default CategorySelector;
