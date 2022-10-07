import Card from '@/components/common/Card/Card';
import { mobileTitleSecondary } from '@/constants/titleType';
import * as S from '@/pages/CategorySelector/CategorySelector.styles';
import { CategorySelectorCardStyle } from '@/styles/cardStyle';

const CategorySelector = () => (
	<S.Container>
		<Card {...CategorySelectorCardStyle}>
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
		</Card>
	</S.Container>
);

export default CategorySelector;
