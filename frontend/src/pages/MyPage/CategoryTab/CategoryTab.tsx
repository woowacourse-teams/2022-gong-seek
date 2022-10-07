import { Dispatch, SetStateAction } from 'react';

import * as S from '@/pages/MyPage/CategoryTab/CategoryTab.styles';
import { CategoryType } from '@/types/myPage';

export interface CategoryTabProps {
	category: CategoryType;
	setCategory: Dispatch<SetStateAction<CategoryType>>;
}

const CategoryTab = ({ category, setCategory }: CategoryTabProps) => {
	const onChangeCategoryClick = (value: CategoryType) => {
		setCategory(value);
	};

	return (
		<S.Container>
			<S.Tab isClicked={category === 'article'} onClick={() => onChangeCategoryClick('article')}>
				내가 작성한 글
			</S.Tab>
			<S.Tab isClicked={category === 'comment'} onClick={() => onChangeCategoryClick('comment')}>
				내가 작성한 댓글
			</S.Tab>
			<S.Tab
				isClicked={category === 'tempArticle'}
				onClick={() => onChangeCategoryClick('tempArticle')}
			>
				임시 저장 글
			</S.Tab>
		</S.Container>
	);
};

export default CategoryTab;
