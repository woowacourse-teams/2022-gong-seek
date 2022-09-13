import { useNavigate, useParams } from 'react-router-dom';

import ArticleItem from '@/components/common/ArticleItem/ArticleItem';
import InfiniteScrollObserver from '@/components/common/InfiniteScrollObserver/InfiniteScrollObserver';
import Loading from '@/components/common/Loading/Loading';
import SortDropdown from '@/components/common/SortDropdown/SortDropDown';
import useGetCategoryArticles from '@/hooks/article/useGetCategoryArticles';
import * as S from '@/pages/CategoryArticles/CategoryArticles.styles';

const CategoryArticles = () => {
	const navigate = useNavigate();
	const { category } = useParams();

	if (typeof category === 'undefined') {
		navigate('/');
		throw new Error('카테고리를 찾을 수 없습니다');
	}

	if (category !== 'discussion' && category !== 'question') {
		navigate('/*');
	}

	const { data, fetchNextPage, sortIndex, setSortIndex, isLoading } =
		useGetCategoryArticles(category);

	if (isLoading) {
		return <Loading />;
	}

	return (
		<S.Container>
			<S.TitleBox>
				<S.CategoryArticlesTitle category={category}>
					{category === 'discussion' ? '토론' : '질문'}
				</S.CategoryArticlesTitle>
				<SortDropdown
					sortList={['최신순', '조회순', '좋아요순']}
					sortIndex={sortIndex}
					setSortIndex={setSortIndex}
				/>
			</S.TitleBox>
			{data?.pages.length ? (
				<InfiniteScrollObserver
					hasNext={data?.pages[data.pages.length - 1].hasNext}
					fetchNextPage={fetchNextPage}
				>
					<S.ArticleItemList>
						{data.pages.map(({ articles }) =>
							articles.map((item) => (
								<ArticleItem
									key={item.id}
									article={item}
									onClick={() => {
										navigate(`/articles/${category}/${item.id}`);
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

export default CategoryArticles;
