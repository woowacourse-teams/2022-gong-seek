import { useNavigate } from 'react-router-dom';

import * as S from '@/pages/Home/ArticleItem/ArticleItem.styles';

interface ArticleItemProps {
	data: {
		id: number;
		title: string;
		commentCount: number;
		category: string;
		author: {
			avatarUrl: string;
			name: string;
		};
	};
}

const ArticleItem = ({ data }: ArticleItemProps) => {
	const navigate = useNavigate();
	const { id, title, author, commentCount, category } = data;
	return (
		<>
			<S.Title onClick={() => navigate(`/articles/${category}/${id}`)}>{title}</S.Title>
			<S.ArticleInfo>
				<S.ProfileBox>
					<S.UserImg alt="유저의 프로필 이미지가 보여지는 곳 입니다 " src={author.avatarUrl} />
					<S.UserName>{author.name}</S.UserName>
				</S.ProfileBox>
				<S.CommentBox>
					<S.CommentCount aria-label="댓글의 개수가 표시되는 곳입니다">
						{commentCount}
					</S.CommentCount>
					<S.CommentIcon />
				</S.CommentBox>
			</S.ArticleInfo>
		</>
	);
};

export default ArticleItem;
