import SortDropdown from './SortDropdown/SortDropDown';
import { useEffect, useState } from 'react';
import { useQuery } from 'react-query';
import { useNavigate, useParams } from 'react-router-dom';

import { getAllArticle } from '@/api/article';
import ArticleItem from '@/components/common/ArticleItem/ArticleItem';
import Loading from '@/components/common/Loading/Loading';
import * as S from '@/pages/CategoryArticles/CategoryArticles.styles';

const CategoryArticles = () => {
	const navigate = useNavigate();
	const { category } = useParams();
	const [sortIndex, setSortIndex] = useState('최신순');
	if (typeof category === 'undefined') {
		navigate('/');
		throw new Error('카테고리를 찾을 수 없습니다');
	}
	const { data, isLoading, isError, refetch } = useQuery('articles', () =>
		getAllArticle(category, sortIndex),
	);

	useEffect(() => {
		refetch();
	}, [sortIndex]);

	if (isLoading) {
		return <Loading />;
	}

	if (isError) {
		return <div> 에러 발생!</div>;
	}

	return (
		<S.Container>
			<S.TitleBox>
				<S.CategoryArticlesTitle category={category}>
					{category === 'discussion' ? '토론' : '에러'}
				</S.CategoryArticlesTitle>
				<SortDropdown sortIndex={sortIndex} setSortIndex={setSortIndex} />
			</S.TitleBox>
			<S.ArticleItemList>
				{data?.articles.map((item) => (
					<ArticleItem
						key={item.id}
						article={item}
						onClick={() => {
							navigate(`/articles/${category}/${item.id}`);
						}}
					/>
				))}
			</S.ArticleItemList>
		</S.Container>
	);
};

export default CategoryArticles;
