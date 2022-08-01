import Loading from "@/components/common/Loading/Loading";
import useGetDetailArticle from "@/hooks/useGetDetailArticle";
import useGetDetailComment from "@/hooks/useGetDetailComment";
import { useParams } from "react-router-dom";
import Detail from "../Detail";
import Vote from "../Discussion/Vote/Vote";

const DiscussionDetail = () => {
  const { id } = useParams();

  if (typeof id === 'undefined') {
		throw new Error('id 값을 받아오지 못했습니다');
  }
  
  const { data: articleData, isLoading: isArticleLoading } = useGetDetailArticle(id);
  const { data: commentData, isLoading: isCommentLoading } = useGetDetailComment(id);

  if (isCommentLoading || isArticleLoading) {
		return <Loading />
	}

  return (
    <>
      {typeof articleData !== 'undefined' && typeof commentData !== 'undefined' && (
        <Detail article={articleData} commentList={commentData.comments} articleId={id}>
            <Vote articleId={id}/>
        </Detail>
      )}
    </>
  )
}

export default DiscussionDetail;