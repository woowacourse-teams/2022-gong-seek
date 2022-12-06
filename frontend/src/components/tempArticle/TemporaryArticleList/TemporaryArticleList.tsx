import React from 'react';
import { useNavigate } from 'react-router-dom';

import { SingleTempArticleItemResponseType } from '@/api/tempArticle/tempArticleType';
import Loading from '@/components/@common/Loading/Loading';
import TemporaryArticleItem from '@/components/tempArticle/TemporaryArticleItem/TemporaryArticleItem';
import * as S from '@/components/tempArticle/TemporaryArticleList/TemporaryArticleList.styles';
import useDeleteTempArticle from '@/hooks/tempArticle/useDeleteTempArticle';
import useGetTempArticles from '@/hooks/tempArticle/useGetTempArticles';

const EmptyMessage = React.lazy(() => import('@/components/@common/EmptyMessage/EmptyMessage'));

const TemporaryArticleList = () => {
	const { data, isLoading } = useGetTempArticles();
	const { deleteTempArticleId } = useDeleteTempArticle();
	const navigate = useNavigate();
	if (isLoading) {
		return <Loading />;
	}

	const handleClickTemporaryArticlekDeleteButton = (id: number) => {
		if (window.confirm('해당 임시 저장 글을 삭제하시겠습니까?')) {
			deleteTempArticleId(id);
		}
	};

	const handleClickTemporaryArticleItem = (item: SingleTempArticleItemResponseType) => {
		navigate(`/temp-article/${item.category}/${item.id}`);
	};

	return (
		<S.Container>
			<h2 hidden>임시저장글들의 목록이 보여지는 곳입니다</h2>
			<S.ArticleListBox>
				{data && data.values.length >= 1 ? (
					data.values.map((item) => (
						<S.ArticleItemBox key={item.id}>
							<TemporaryArticleItem
								article={item}
								onClick={() => handleClickTemporaryArticleItem(item)}
							/>
							<S.DeleteButton onClick={() => handleClickTemporaryArticlekDeleteButton(item.id)}>
								<S.DeleteIcon />
							</S.DeleteButton>
						</S.ArticleItemBox>
					))
				) : (
					<EmptyMessage>임시저장한 글이 없습니다</EmptyMessage>
				)}
			</S.ArticleListBox>
		</S.Container>
	);
};
export default TemporaryArticleList;
