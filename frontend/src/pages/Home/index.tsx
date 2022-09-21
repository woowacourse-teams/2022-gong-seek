import React, { Suspense, useRef } from 'react';
import { useNavigate } from 'react-router-dom';

import ArticleItem from '@/components/common/ArticleItem/ArticleItem';
import InfiniteScrollObserver from '@/components/common/InfiniteScrollObserver/InfiniteScrollObserver';
import Loading from '@/components/common/Loading/Loading';
import SortDropdown from '@/components/common/SortDropdown/SortDropDown';
import useGetAllArticles from '@/hooks/article/useGetAllArticles';
import PopularArticle from '@/pages/Home/PopularArticle/PopularArticle';
import * as S from '@/pages/Home/index.styles';

// TODO: 과도한 레이징 로딩! 컴포넌트단은 레이지 로딩을 할시 더 느려질것 같다.
// const InfiniteScrollObserver = React.lazy(
// 	() => import('@/components/common/InfiniteScrollObserver/InfiniteScrollObserver'),
// );
// const ArticleItem = React.lazy(() => import('@/components/common/ArticleItem/ArticleItem'));
// const PopularArticle = React.lazy(() => import('@/pages/Home/PopularArticle/PopularArticle'));

const Home = () => {
	const endFlag = useRef<HTMLDivElement>(null);
	const navigate = useNavigate();

	const {
		data,
		currentCategory,
		setCurrentCategory,
		sortIndex,
		setSortIndex,
		fetchNextPage,
		isLoading,
	} = useGetAllArticles();

	return (
		<S.Container ref={endFlag}>
			{/*TODO: 오늘의 인기글이랑 PopularArticle 합쳐서 컴포넌트로! */}
			<S.PopularArticleTitle>오늘의 인기글</S.PopularArticleTitle>
			<Suspense fallback={<Loading />}>
				<PopularArticle />
			</Suspense>
			{/* CompoundComponent 패턴 어떨까? */}
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
					sortList={['최신순', '조회순', '좋아요순']}
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
						{/*TODO: map 부분을  컴포넌트로 빼자!, ArticleItemList안에 Suspense내부를 모두 빼서 넣는것이 좋을것 같다!*/}
						<S.ArticleItemList>
							{data.pages.map(({ articles }) =>
								articles.map((item) => (
									//TODO: 가장 큰 문제.. 현재 ArticleItem이 2개다, HOME안에 ArticleItem을 선언했는데 실제로는 common에서 불러와서 사용하고 있다.
									<ArticleItem
										key={item.id}
										article={item}
										isLoading={isLoading}
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
