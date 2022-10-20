import { Dispatch, SetStateAction } from 'react';

import * as S from '@/components/user/MyCategoryTab/MyCategoryTab.styles';
import { CategoryType } from '@/types/myPage';

export interface MyCategoryTabProps {
	category: CategoryType;
	setCategory: Dispatch<SetStateAction<CategoryType>>;
}

const MyCategoryTab = ({ category, setCategory }: MyCategoryTabProps) => {
	const handleChangeTab = (value: CategoryType) => {
		setCategory(value);
	};

	return (
		<S.Container>
			<S.Tab isClicked={category === 'article'} onClick={() => handleChangeTab('article')}>
				내가 작성한 글
			</S.Tab>
			<S.Tab isClicked={category === 'comment'} onClick={() => handleChangeTab('comment')}>
				내가 작성한 댓글
			</S.Tab>
			<S.Tab isClicked={category === 'tempArticle'} onClick={() => handleChangeTab('tempArticle')}>
				임시 저장 글
			</S.Tab>
		</S.Container>
	);
};

export default MyCategoryTab;
