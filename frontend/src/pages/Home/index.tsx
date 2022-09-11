import { useRef } from 'react';
import { useNavigate } from 'react-router-dom';

import ArticleItem from '@/components/common/ArticleItem/ArticleItem';
import InfiniteScrollObserver from '@/components/common/InfiniteScrollObserver/InfiniteScrollObserver';
import Loading from '@/components/common/Loading/Loading';
import SortDropdown from '@/components/common/SortDropdown/SortDropDown';
import PopularArticle from '@/pages/Home/PopularArticle/PopularArticle';
import useGetAllArticles from '@/pages/Home/hooks/useGetAllArticles';
import * as S from '@/pages/Home/index.styles';

const Home = () => {
	const endFlag = useRef<HTMLDivElement>(null);
	const navigate = useNavigate();

	const {
		data,
		isLoading,
		currentCategory,
		setCurrentCategory,
		sortIndex,
		setSortIndex,
		fetchNextPage,
	} = useGetAllArticles();

	if (isLoading) {
		return <Loading />;
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
				<S.EmptyText>텅 비었어요..!</S.EmptyText>
			)}
		</S.Container>
	);
};

export default Home;
