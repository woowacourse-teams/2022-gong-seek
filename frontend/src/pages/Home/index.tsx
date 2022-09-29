import React, { Suspense, useRef } from 'react';
import { useNavigate } from 'react-router-dom';

import Loading from '@/components/common/Loading/Loading';
import SortDropdown from '@/components/common/SortDropdown/SortDropDown';
import useGetAllArticles from '@/hooks/article/useGetAllArticles';
import * as S from '@/pages/Home/index.styles';

const InfiniteScrollObserver = React.lazy(
	() => import('@/components/common/InfiniteScrollObserver/InfiniteScrollObserver'),
);
const ArticleItem = React.lazy(() => import('@/components/common/ArticleItem/ArticleItem'));
const PopularArticle = React.lazy(() => import('@/pages/Home/PopularArticle/PopularArticle'));

const Home = () => {
	const endFlag = useRef<HTMLDivElement>(null);
	const navigate = useNavigate();

	const { data, currentCategory, setCurrentCategory, sortIndex, setSortIndex, fetchNextPage } =
		useGetAllArticles();

	return (
		<S.Container ref={endFlag}>
			<S.PopularArticleTitle>오늘의 인기글</S.PopularArticleTitle>
			<Suspense fallback={<Loading />}>
				<PopularArticle />
			</Suspense>
			<S.CategoryTitleContainer>
				<S.CategoryTitleBox>
					<S.CategoryTitle
						isActive={currentCategory === 'question'}
						onClick={() => setCurrentCategory('question')}
					>
						질문
					</S.CategoryTitle>
					<S.CategoryTitle
						isActive={currentCategory === 'discussion'}
						onClick={() => setCurrentCategory('discussion')}
					>
						토론
					</S.CategoryTitle>
				</S.CategoryTitleBox>
				<SortDropdown
					sortList={['최신순', '조회순', '추천순']}
					sortIndex={sortIndex}
					setSortIndex={setSortIndex}
				/>
			</S.CategoryTitleContainer>
			<Suspense fallback={<Loading />}>
				{data?.pages.length ? (
					<InfiniteScrollObserver
						hasNext={data.pages[data.pages.length - 1].hasNext}
						fetchNextPage={fetchNextPage}
					>
						<S.ArticleItemList>
							{data.pages.map(({ articles }) =>
								articles.map((item) => (
									<ArticleItem
										key={item.id}
										article={item}
										onClick={() => {
											navigate(`/articles/${currentCategory}/${item.id}`);
										}}
									/>
								)),
							)}
						</S.ArticleItemList>
					</InfiniteScrollObserver>
				) : (
					<S.EmptyText>게시물이 존재하지 않습니다</S.EmptyText>
				)}
			</Suspense>
		</S.Container>
	);
};

export default Home;
