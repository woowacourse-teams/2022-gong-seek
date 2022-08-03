import PageLayout from '@/components/layout/PageLayout/PageLayout';
import { mobileTitleSecondary } from '@/constants/titleType';
import * as S from '@/pages/CategorySelector/CategorySelector.styles';

const CategorySelector = () => (
	<S.Container>
		<PageLayout
			width="80%"
			maxWidth="25rem"
			height="16rem"
			flexDirection="column"
			justifyContent="space-around"
		>
			<h2 css={mobileTitleSecondary}>글쓰기 카테고리 선택</h2>
			<S.CategoryButtonBox>
				<S.StyledLink to="/article/question">
					<S.CategoryButton type="button" categoryType="question">
						질문
					</S.CategoryButton>
				</S.StyledLink>
				<S.StyledLink to="/article/discussion">
					<S.CategoryButton type="button" categoryType="discussion">
						토론
					</S.CategoryButton>
				</S.StyledLink>
			</S.CategoryButtonBox>
		</PageLayout>
	</S.Container>
);

export default CategorySelector;
