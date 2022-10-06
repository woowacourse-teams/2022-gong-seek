import { useNavigate, useParams } from 'react-router-dom';

import ArticleItem from '@/components/@common/ArticleItem/ArticleItem';
import EmptyMessage from '@/components/@common/EmptyMessage/EmptyMessage';
import Loading from '@/components/@common/Loading/Loading';
import ResponsiveInfiniteCardList from '@/components/@common/ResponsiveInfiniteCardList/ResponsiveInfiniteCardList';
import SortDropdown from '@/components/@common/SortDropdown/SortDropDown';
import { URL } from '@/constants/url';
import useGetCategoryArticles from '@/hooks/article/useGetCategoryArticles';
import * as S from '@/pages/CategoryArticles/CategoryArticles.styles';
import { categoryNameConverter } from '@/utils/converter';

const CategoryArticles = () => {
	const navigate = useNavigate();
	const { category } = useParams();

	if (typeof category === 'undefined') {
		navigate(URL.HOME);
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
					{categoryNameConverter(category)}
				</S.CategoryArticlesTitle>
				<SortDropdown
					sortList={['최신순', '조회순', '추천순']}
					sortIndex={sortIndex}
					setSortIndex={setSortIndex}
				/>
			</S.TitleBox>
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
									onClick={() => {
										navigate(`/articles/${category}/${item.id}`);
									}}
								/>
							)),
						)}
					</>
				</ResponsiveInfiniteCardList>
			) : (
				<EmptyMessage>게시글이 존재하지 않습니다</EmptyMessage>
			)}
		</S.Container>
	);
};

export default CategoryArticles;
