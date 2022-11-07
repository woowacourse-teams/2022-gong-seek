import React, { Suspense } from 'react';
import { useNavigate } from 'react-router-dom';

import { ArticleTotalType } from '@/api/article/articleType';
import EmptyMessage from '@/components/@common/EmptyMessage/EmptyMessage';
import Loading from '@/components/@common/Loading/Loading';
import SortDropdown from '@/components/@common/SortDropdown/SortDropDown';
import useGetAllArticles from '@/hooks/article/useGetAllArticles';
import useEnterToClick from '@/hooks/common/useEnterToClick';
import * as S from '@/pages/Home/index.styles';

const ResponsiveInfiniteCardList = React.lazy(
	() => import('@/components/@common/ResponsiveInfiniteCardList/ResponsiveInfiniteCardList'),
);
const ArticleItem = React.lazy(() => import('@/components/article/ArticleItem/ArticleItem'));
const PopularArticle = React.lazy(
	() => import('@/components/article/PopularArticle/PopularArticle'),
);

const Home = () => {
	const [enterRef] = useEnterToClick();

	const navigate = useNavigate();

	const { data, currentCategory, setCurrentCategory, sortIndex, setSortIndex, fetchNextPage } =
		useGetAllArticles();

	const handleClickCategoryTitle = (category: 'question' | 'discussion') => {
		setCurrentCategory(category);
	};

	const handleLinkToArticleDetail = (
		item: Omit<ArticleTotalType, 'updatedAt' | 'isAuthor' | 'hasVote'>,
	) => {
		navigate(`/articles/${currentCategory}/${item.id}`);
	};

	return (
		<S.Container ref={enterRef}>
			<S.PopularArticleTitle id="popular-articles">오늘의 인기글</S.PopularArticleTitle>
			<Suspense fallback={<Loading />}>
				<PopularArticle />
			</Suspense>
			<S.CategoryTitleContainer>
				<S.CategoryTitleBox>
					<S.CategoryTitle
						isActive={currentCategory === 'question'}
						onClick={() => handleClickCategoryTitle('question')}
						tabIndex={0}
						aria-pressed={currentCategory === 'question'}
						role="button"
						aria-live="polite"
					>
						질문
					</S.CategoryTitle>
					<S.CategoryTitle
						isActive={currentCategory === 'discussion'}
						onClick={() => setCurrentCategory('discussion')}
						tabIndex={0}
						aria-pressed={currentCategory === 'discussion'}
						role="button"
						aria-live="polite"
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
					<ResponsiveInfiniteCardList
						hasNext={data.pages[data.pages.length - 1].hasNext}
						fetchNextPage={fetchNextPage}
					>
						<>
							{data.pages.map(({ articles }) =>
								articles.map((item) => (
									<ArticleItem
										key={item.id}
										article={item}
										onClick={() => handleLinkToArticleDetail(item)}
									/>
								)),
							)}
						</>
					</ResponsiveInfiniteCardList>
				) : (
					<EmptyMessage>게시글이 존재하지 않습니다</EmptyMessage>
				)}
			</Suspense>
		</S.Container>
	);
};

export default Home;
