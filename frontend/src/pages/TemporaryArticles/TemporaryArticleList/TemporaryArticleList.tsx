import { useNavigate } from 'react-router-dom';

import Loading from '@/components/common/Loading/Loading';
import useDeleteTempArticle from '@/hooks/tempArticle/useDeleteTempArticle';
import useGetTempArticles from '@/hooks/tempArticle/useGetTempArticles';
import TemporaryArticleItem from '@/pages/TemporaryArticles/TemporaryArticleItem/TemporaryArticleItem';
import * as S from '@/pages/TemporaryArticles/TemporaryArticleList/TemporaryArticleList.styles';

const TemporaryArticleList = () => {
	const { data, isLoading } = useGetTempArticles();
	const { deleteTempArticleId } = useDeleteTempArticle();
	const nav = useNavigate();
	if (isLoading) {
		return <Loading />;
	}

	const handleTempArticleDeleteClick = (id: number) => {
		if (window.confirm('해당 임시 저장 글을 삭제하시겠습니까?')) {
			deleteTempArticleId(id);
		}
	};

	return (
		<S.Container>
			<h2 hidden>임시저장글들의 목록이 보여지는 곳입니다</h2>
			<S.ArticleListBox>
				{data ? (
					data.values.map((item) => (
						<S.ArticleItemBox key={item.id}>
							<TemporaryArticleItem
								article={{ title: item.title, createAt: item.createAt }}
								onClick={() => nav(`/temp-article/${item.category}/${item.id}`)}
							/>
							<S.DeleteButton onClick={() => handleTempArticleDeleteClick(item.id)} />
						</S.ArticleItemBox>
					))
				) : (
					<S.EmptyArticleMessage>임시저장한 글이 존재하지 않습니다</S.EmptyArticleMessage>
				)}
			</S.ArticleListBox>
		</S.Container>
	);
};
export default TemporaryArticleList;
