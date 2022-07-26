import { useEffect, useState } from 'react';
import { useRef } from 'react';
import { useInfiniteQuery, useQuery } from 'react-query';
import { useNavigate } from 'react-router-dom';

import { getAllArticle } from '@/api/article';
import ArticleItem from '@/components/common/ArticleItem/ArticleItem';
import Loading from '@/components/common/Loading/Loading';
import SortDropdown from '@/pages/CategoryArticles/SortDropdown/SortDropDown';
import PopularArticle from '@/pages/Home//PopularArticle/PopularArticle';
import * as S from '@/pages/Home/index.styles';
import { CommonArticleType } from '@/types/articleResponse';

interface AllArticleResponse {
	articles: CommonArticleType[];
	hasNext: boolean;
	pageParam: number;
}

const Home = () => {
	const [currentCategory, setCurrentCategory] = useState('question');
	const [sortIndex, setSortIndex] = useState('최신순');
	const endFlag = useRef<HTMLDivElement>(null);

	const navigate = useNavigate();

	const { data, isError, isLoading, isSuccess, error, refetch } = useQuery('all-articles', () =>
		getAllArticle(currentCategory, sortIndex),
	);
	// const { data, isError, isLoading, isSuccess, error, refetch } = useInfiniteQuery<
	// 	AllArticleResponse,
	// 	Error
	// >(
	// 	['all-articles', currentCategory],
	// 	({ pageParam = 0 }) => getAllArticle(currentCategory, sortIndex, pageParam),
	// 	{
	// 		getNextPageParam: (lastPage) => {
	// 			if (lastPage.articles.length === 5) return { page: lastPage.hasNext };
	// 			return;
	// 		},
	// 	},
	// );

	useEffect(() => {
		console.log(data);
	}, [isSuccess]);

	useEffect(() => {
		refetch();
	}, [currentCategory, sortIndex]);

	if (isSuccess) {
		if (data.articles.length === 0) {
			return <div>게시글이 존재하지 않습니다</div>;
		}
	}

	if (isLoading) {
		return <Loading />;
	}
	if (isError) {
		return <div>에러</div>;
	}

	return (
		<S.Container ref={endFlag}>
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
				<SortDropdown sortIndex={sortIndex} setSortIndex={setSortIndex} />
			</S.CategoryTitleContainer>
			<S.ArticleItemList>
				{data?.articles.map((item) => (
					<ArticleItem
						key={item.id}
						article={item}
						onClick={() => {
							navigate(`/articles/${currentCategory}/${item.id}`);
						}}
					/>
				))}
			</S.ArticleItemList>
		</S.Container>
	);
};

export default Home;
