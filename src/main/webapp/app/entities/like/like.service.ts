import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILike } from 'app/shared/model/like.model';

type EntityResponseType = HttpResponse<ILike>;
type EntityArrayResponseType = HttpResponse<ILike[]>;

@Injectable({ providedIn: 'root' })
export class LikeService {
  public resourceUrl = SERVER_API_URL + 'api/likes';

  constructor(protected http: HttpClient) {}

  create(like: ILike): Observable<EntityResponseType> {
    return this.http.post<ILike>(this.resourceUrl, like, { observe: 'response' });
  }

  update(like: ILike): Observable<EntityResponseType> {
    return this.http.put<ILike>(this.resourceUrl, like, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILike>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILike[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
