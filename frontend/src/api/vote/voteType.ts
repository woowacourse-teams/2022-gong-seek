export interface SingleVoteItemType {
	id: number;
	content: string;
	amount: number;
}

export interface VoteResponseType {
	articleId: string;
	voteItems: SingleVoteItemType[];
	votedItemId: number | null;
	expired: boolean;
}

export interface CreateVoteRequestType {
	items: string[];
	expiryDate: string;
	articleId: string;
}

export interface CheckVoteRequestType {
	voteItemId: string;
	articleId: string;
}
