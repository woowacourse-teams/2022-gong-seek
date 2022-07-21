import PopularArticle from '@/pages/Home//PopularArticle/PopularArticle';
import { useState } from 'react';

import ArticleItem from '@/components/common/ArticleItem/ArticleItem';
import SortDropdown from '@/pages/CategoryArticles/SortDropdown/SortDropDown';
import * as S from '@/pages/Home/index.styles';

const Home = () => {
	const [currentCategory, setCurrentCategory] = useState('question');

	return (
		<S.Container>
			<S.PopularArticleTitle>오늘의 인기글</S.PopularArticleTitle>
			<PopularArticle />
			<S.CategoryTitleContainer>
				<S.CategoryTitleBox>
					<S.CategoryTitle
						isActive={currentCategory === 'question'}
						onClick={() => setCurrentCategory('question')}
					>
						에러
					</S.CategoryTitle>
					<S.CategoryTitle
						isActive={currentCategory === 'discussion'}
						onClick={() => setCurrentCategory('discussion')}
					>
						토론
					</S.CategoryTitle>
				</S.CategoryTitleBox>
				<SortDropdown />
			</S.CategoryTitleContainer>
			<S.ArticleItemList>
				<ArticleItem />
				<ArticleItem />
				<ArticleItem />
				<ArticleItem />
				<ArticleItem />
			</S.ArticleItemList>
		</S.Container>
	);
};

export default Home;
